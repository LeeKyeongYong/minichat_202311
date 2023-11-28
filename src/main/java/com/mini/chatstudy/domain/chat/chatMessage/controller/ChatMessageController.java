package com.mini.chatstudy.domain.chat.chatMessage.controller;


import com.mini.chatstudy.domain.chat.chatMessage.dto.ChatMessageDto;
import com.mini.chatstudy.domain.chat.chatMessage.entity.ChatMessage;
import com.mini.chatstudy.domain.chat.chatMessage.service.ChatMessageService;
import com.mini.chatstudy.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@Validated
public class ChatMessageController{

    private final ChatMessageService chatMessageService;

    @GetMapping("/room/{roomId}")
    public String showRoom(final @PathVariable String roomId, final Model model){

        model.addAttribute("roomId",roomId);
        return "chat/chatMessage/room";
    }

    public record WriteMessageRequestBody(@NotBlank String writerName,@NotBlank String body){

    }

    @Getter
    public static class WriteMessageResponseBody{
        private final ChatMessageDto message;

        public WriteMessageResponseBody(ChatMessage message){
            this.message=new ChatMessageDto(message);
        }
    }

    @MessageMapping("/chat/room/{roomId}/message")
    @SendTo("/topic/chat/room/{roomId}/messages")
    public RsData<WriteMessageResponseBody> writeMessage(@DestinationVariable final long roomId,
                                                         @Valid @Payload final WriteMessageRequestBody  requestBody){
        RsData<ChatMessage> writeRs = chatMessageService.write(roomId,requestBody.writerName,requestBody.body);
        return writeRs.newDataOf(new WriteMessageResponseBody(writeRs.getData()));
    }

    @Getter
    public static class GetMessagesResponseBody{
        private final List<ChatMessageDto> messages;
        public GetMessagesResponseBody(List<ChatMessage> messages){
            this.messages = messages
                    .stream()
                    .map(ChatMessageDto::new)
                    .toList();
        }
    }

    @GetMapping("/room/{roomId}/messages/{fromId}")
    @ResponseBody
    public RsData<GetMessagesResponseBody> getMessages(@PathVariable final long roomId,@PathVariable final long fromId){
        List<ChatMessage> messages = chatMessageService.findByChatRoomIdAndIdAfter(roomId, fromId);
        return RsData.of("S-1","성공",new GetMessagesResponseBody(messages));
    }
}