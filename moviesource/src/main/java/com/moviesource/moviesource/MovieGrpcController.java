package com.moviesource.moviesource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moviesource.moviesource.model.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
@GRpcService
public class MovieGrpcController extends MovieServiceGrpc.MovieServiceImplBase {

    @Autowired
    private MovieService movieService;

    @Override
    public void getNowShowing(NowShowingRequest request, StreamObserver<NowShowingResponse> responseObserver) {
        try {
            responseObserver.onNext(movieService.getNowShowing(request));
        } catch (JsonProcessingException exception) {
            responseObserver.onError(exception);
        }
        responseObserver.onCompleted();
    }
}