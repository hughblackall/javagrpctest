package com.moviepresenter.moviepresenter;

import com.moviesource.moviesource.model.MovieServiceGrpc;
import com.moviesource.moviesource.model.NowShowingRequest;
import com.moviesource.moviesource.model.NowShowingResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MovieGrpcClient {

    private final int moviesourcePort = 6789;
    private final String moviesourceName = "localhost";

    public NowShowingResponse getNowShowing() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(moviesourceName, moviesourcePort).usePlaintext().build();
        MovieServiceGrpc.MovieServiceBlockingStub client = MovieServiceGrpc.newBlockingStub(channel);

        NowShowingResponse response = client.getNowShowing(NowShowingRequest.newBuilder()
                .setTimestamp(123456789L)
                .build());

        channel.shutdown();

        return response;
    }
}
