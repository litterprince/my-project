package com.test.grpc.search.simplerpc;

import com.test.grpc.search.SearchRequest;
import com.test.grpc.search.SearchResponse;
import com.test.grpc.search.SearchServiceGrpc;
import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleSearchServer {
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
            SimpleSearchServer.this.stop();
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
        final SimpleSearchServer server = new SimpleSearchServer();
        server.start();
        server.blockUntilShutdown();
    }

    // 实现 定义一个实现服务接口的类
    private class SearchServiceImpl extends SearchServiceGrpc.SearchServiceImplBase {

        public void searchWithSimpleRpc(SearchRequest request, StreamObserver<SearchResponse> responseObserver) {
            System.out.println("pageNo=" + request.getPageNumber());
            System.out.println("query string=" + request.getQuery());
            System.out.println("pageSize=" + request.getResultPerPage());

            List<SearchResponse.Result> results = new ArrayList<>(10);
            for (int i = 0; i < request.getResultPerPage(); i++) {
                SearchResponse.Result result = SearchResponse.Result.newBuilder()
                    .setTitle("title" + i).setUrl("dev.usoft.com")
                    .addSnippets("snippets" + i).build();
                results.add(result);
            }

            SearchResponse response = SearchResponse.newBuilder().addAllResult(results).build();
            responseObserver.onNext(response);
            //We use the response observer's onCompleted() method to specify that we've finished dealing with the RPC.
            responseObserver.onCompleted();
        }
    }
}
