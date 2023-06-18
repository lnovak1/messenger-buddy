package com.novak.neuralresponser.controllers;

import ch.qos.logback.core.net.server.Client;
import com.google.gson.Gson;
import com.novak.neuralresponser.controllers.dtos.NeuralStartDto;
import com.novak.neuralresponser.domain.NeuralProcessRequest;
import com.novak.neuralresponser.services.neural.NeuralConnector;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.*;
import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/neuralController")
public class NeuralController {
    public static String BASE_URL = "localhost:8080";
    private final NeuralConnector neuralConnector;
    private final Gson gson;

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
            ResponseBody body = response.body();
            String string = body.string();

            NeuralStartDto neuralStartDto = gson.fromJson(string, NeuralStartDto.class);
            neuralConnector.startConnection(neuralStartDto.getApiKey());
            return ResponseEntity.status(HttpStatus.OK).body("Connection started");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to start Neural connection");
        }

    }

    @SneakyThrows
    @GetMapping(value = "/start")
    public ResponseEntity<NeuralStartDto> get(){

        Dotenv load = Dotenv.configure()
                .directory("./")
                .load();
        String gptApiKey = load.get("GPT_API_KEY");

        NeuralStartDto neuralStartDto = new NeuralStartDto();
        neuralStartDto.setApiKey(gptApiKey);

        return ResponseEntity.status(HttpStatus.OK).body(neuralStartDto);
    }


}
