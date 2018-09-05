package com.test.grpc.search.bidrectionalstream;

import com.google.common.util.concurrent.SettableFuture;
import com.test.grpc.search.SearchRequest;
import com.test.grpc.search.SearchResponse;
import com.test.grpc.search.SearchServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ExecutionException;

public class BidirectionalStreamRpcClient {
    private final static int port = 8051;

    private final ManagedChannel channel;
    private final SearchServiceGrpc.SearchServiceBlockingStub blockingStub;
    private final SearchServiceGrpc.SearchServiceStub asyncStub;

    public BidirectionalStreamRpcClient(int port) {
        channel = ManagedChannelBuilder.forAddress("127.0.0.1", port).usePlaintext(true).build();
        blockingStub = SearchServiceGrpc.newBlockingStub(channel);
        asyncStub = SearchServiceGrpc.newStub(channel);
    }

    /**
     * simple rpc
     *
     * @param pageNo
     * @param pageSize
     */
    public void searchWithBidirectionalStreamRpc(int pageNo, int pageSize) throws ExecutionException, InterruptedException {
        final SettableFuture<Void> finishFuture = SettableFuture.create();
        StreamObserver<SearchRequest> requestObserver = asyncStub
            .searchWithBidirectionalStreamRpc(
                new StreamObserver<SearchResponse>() {
                    @Override
                    public void onNext(SearchResponse searchResponse) {
                        System.out.println("response with result = \n" + searchResponse.toString());
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        finishFuture.setException(throwable);
                    }
                    @Override
                    public void onCompleted() {
                        finishFuture.set(null);
                    }
                });
        try {
            // 发送三次search request
            for (int i = 1; i <= 3; i++) {
                SearchRequest request = SearchRequest.newBuilder()
                        .setPageNumber(pageNo).setResultPerPage(pageSize + i)
                        .build();
                requestObserver.onNext(request);
            }
            requestObserver.onCompleted();
            finishFuture.get();
            System.out.println("finished");
        } catch (Exception e) {
            requestObserver.onError(e);
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
         BidirectionalStreamRpcClient client = new BidirectionalStreamRpcClient(port);
        client.searchWithBidirectionalStreamRpc(1,1);
    }
}
