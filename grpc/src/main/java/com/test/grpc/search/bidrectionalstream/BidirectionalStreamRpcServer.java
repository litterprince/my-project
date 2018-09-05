package com.test.grpc.search.bidrectionalstream;

import com.test.grpc.search.SearchRequest;
import com.test.grpc.search.SearchResponse;
import com.test.grpc.search.SearchServiceGrpc;
import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BidirectionalStreamRpcServer {
    private int port = 8051;
    private io.grpc.Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService((BindableService) new SearchServiceImpl())
                .build()
                .start();

        System.out.println("service start...");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            BidirectionalStreamRpcServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // block 一直到退出程序
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final BidirectionalStreamRpcServer server = new BidirectionalStreamRpcServer();
        server.start();
        server.blockUntilShutdown();
    }

    // 实现 定义一个实现服务接口的类
    private class SearchServiceImpl extends SearchServiceGrpc.SearchServiceImplBase {

        public StreamObserver<SearchRequest> searchWithBidirectionalStreamRpc(final StreamObserver<SearchResponse> responseObserver) {
            return new StreamObserver<SearchRequest>() {
                int searchCount;
                SearchRequest previous;
                long startTime = System.nanoTime();

                @Override
                public void onNext(SearchRequest searchRequest) {
                    searchCount++;
                    if (previous != null
                            && previous.getResultPerPage() == searchRequest
                            .getResultPerPage()
                            && previous.getPageNumber() == searchRequest
                            .getPageNumber()) {
                        System.out.println("do nothing");
                        return;
                    }
                    previous = searchRequest;
                    System.out.println("search count = " + searchCount);
                    List<SearchResponse.Result> results = new ArrayList<>(10);
                    for (int i = 0; i < searchRequest.getResultPerPage(); i++) {
                        SearchResponse.Result result = SearchResponse.Result
                                .newBuilder().setTitle("title" + i)
                                .setUrl("dev.usoft.com").addSnippets("snippets" + i)
                                .build();
                        results.add(result);
                    }
                    SearchResponse response = SearchResponse.newBuilder().addAllResult(results).build();
                    responseObserver.onNext(response);
                    System.out.println("spend time = " + String.valueOf(System.nanoTime() - startTime));
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("error");
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }
    }
}
