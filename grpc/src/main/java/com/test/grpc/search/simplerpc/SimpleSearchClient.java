package com.test.grpc.search.simplerpc;

import com.test.grpc.search.SearchRequest;
import com.test.grpc.search.SearchResponse;
import com.test.grpc.search.SearchServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SimpleSearchClient {
    private final static int port = 8051;

    private final ManagedChannel channel;
    private final SearchServiceGrpc.SearchServiceBlockingStub blockingStub;
    private final SearchServiceGrpc.SearchServiceStub asyncStub;

    public SimpleSearchClient(int port) {
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
    public void searchWithSimpleRpc(int pageNo, int pageSize) {
        try {
            System.out.println("search param pageNo=" + pageNo + ",pageSize=" + pageSize);
            SearchRequest request = SearchRequest.newBuilder().setPageNumber(pageNo).setResultPerPage(pageSize).build();
            SearchResponse response = blockingStub.searchWithSimpleRpc(request);
            System.out.println("search result: " + response.toString());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return;
        }
    }

    public static void main(String[] args) {
         SimpleSearchClient client = new SimpleSearchClient(port);
        client.searchWithSimpleRpc(1,1);
    }
}
