package com.mini.chatstudy.domain.chat.chatMessage.service;

import com.mini.chatstudy.domain.chat.chatMessage.entity.ChatMessage;
import com.mini.chatstudy.domain.chat.chatMessage.repository.ChatMessageRepository;
import com.mini.chatstudy.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public RsData<ChatMessage>Write(long chatRoomId,String wirterName,String body){
        ChatMessage chatMessage = ChatMessage
                .builder()
                .chatRoomId()
                .writerName()
                .body(body)
                .build();

        chatMessageRepository.save(chatMessage);

        return RsData.of("S-1", "%d번 메세지가 생성되었습니다.".formatted(chatMessage.getId()), chatMessage);
    }
    public List<ChatMessage> findByChatRoomIdAndIdAfter(long roomId,long fromId){
        return chatMessageRepository.findbyChatRoomIdAndIdAfter(roomId,fromId);
    }
}
