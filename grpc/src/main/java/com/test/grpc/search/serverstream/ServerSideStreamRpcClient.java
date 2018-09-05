package com.test.grpc.search.serverstream;

import com.test.grpc.search.SearchRequest;
import com.test.grpc.search.SearchResponse;
import com.test.grpc.search.SearchServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class ServerSideStreamRpcClient {
    private final static int port = 8051;

    private final ManagedChannel channel;
    private final SearchServiceGrpc.SearchServiceBlockingStub blockingStub;
    private final SearchServiceGrpc.SearchServiceStub asyncStub;

    public ServerSideStreamRpcClient(int port) {
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
    public void searchWithSeverSideStreamRpc(int pageNo, int pageSize) {
        try {
            System.out.println("search param pageNo=" + pageNo + ",pageSize=" + pageSize);
            SearchRequest request = SearchRequest.newBuilder().setPageNumber(pageNo).setResultPerPage(pageSize).build();
            Iterator<SearchResponse> responseIterator = blockingStub.searchWithServerSideStreamRpc(request);
            while (responseIterator.hasNext()) {
                SearchResponse r = responseIterator.next();
                if (r.getResult(0).getSnippets(0).equals("the last")) {
                    System.out.println("the end: \n" + r.toString());
                    break;
                }
                System.out.println("search result:\n " + r.toString());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return;
        }
    }

    public static void main(String[] args) {
        ServerSideStreamRpcClient client = new ServerSideStreamRpcClient(port);
        client.searchWithSeverSideStreamRpc(1,1);
    }
}
