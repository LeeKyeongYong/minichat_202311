package com.mini.chatstudy.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class RsData<T> {

    private String resultcode;
    private String msg;
    private T data;

    public static <T> RsData<T> of(String resultcode,String msg,T data){
        return new RsData<>(resultcode,msg,data);
    }

    public static <T> RsData<T> of(String resultCode,String msg){
        return of(resultCode,msg,null);
    }

    public boolean isSuccess(){
        return resultcode.startsWith("S-");
    }

    public boolean isFail(){
        return !isSuccess();
    }

    public Optional<RsData<T>> optional(){
        return Optional.of(this);
    }

    public <T> RsData<T> newDataOf(T data){
        return new RsData<T>(getResultcode(),getMsg(),data);
    }
}
