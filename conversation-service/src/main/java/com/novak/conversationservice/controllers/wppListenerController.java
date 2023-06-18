package com.novak.conversationservice.controllers;


import com.google.gson.Gson;
import com.novak.conversationservice.controllers.dtos.ConversationDto;
import com.novak.conversationservice.conversation.ConversationConnector;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/conversation")
public class wppListenerController {

    private final ConversationConnector conversationConnector;
    private final Gson gson;
    @SneakyThrows
    @PostMapping(value = "/wpp-listener")
    public ResponseEntity startConnection(@RequestBody String string){
            log.info("RECEIVING WPP MSG::::::::::{}",string);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @SneakyThrows
    @PostMapping(value = "/start")
    public ResponseEntity startConnection(){
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
//                .url(BASE_URL + "/api/tokens/gpt")
                    .url("http://localhost:8200/neuralController/start")
                    .method("GET",null)
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            okhttp3.ResponseBody body = response.body();
            String string = body.string();

            ConversationDto conversationDto = gson.fromJson(string, ConversationDto.class);
            conversationConnector.startConnection(conversationDto.getAccountSid(),conversationDto.getTwilioToken());
            return ResponseEntity.status(HttpStatus.OK).body("Connection started");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to start Neural connection");
        }

    }

    @SneakyThrows
    @GetMapping(value = "/start")
    public ResponseEntity<ConversationDto> get(){

        Dotenv load = Dotenv.configure()
                .directory("./")
                .load();
        String accountSid = load.get("ACCOUNT_SID");
        String twilioToken = load.get("TWILIO_TOKEN");

        ConversationDto conversationDto = new ConversationDto();
        conversationDto.setAccountSid(accountSid);
        conversationDto.setTwilioToken(twilioToken);
        conversationConnector.startConnection(accountSid,twilioToken);

        return ResponseEntity.status(HttpStatus.OK).body(conversationDto);
    }

    @SneakyThrows
    @PostMapping(value = "/stop")
    public ResponseEntity endConnection(){
        try{

            conversationConnector.endConnection();
            return ResponseEntity.status(HttpStatus.OK).body("Connection stopped");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to stopped Neural connection");
        }

    }
}
