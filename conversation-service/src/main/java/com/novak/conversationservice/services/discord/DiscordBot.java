package com.novak.conversationservice.services.discord;

import com.novak.conversationservice.custom.NeuralConsumerCustomListener;
import com.novak.conversationservice.domain.NeuralProcessRequest;
import com.novak.conversationservice.domain.NeuralProcessResponse;
import com.novak.conversationservice.services.conversation.ConversationConnector;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

@Log4j2
@Service
@RequiredArgsConstructor
public class DiscordBot extends ListenerAdapter implements ConversationConnector {

    public static String USER_ID_COMMAND = "user-id";
    public static String MESSAGE_COMMAND = "msg";
    public static String GPT_KEY_COMMAND = "gpt-key";
    public static String BUDDY_COMMAND = "buddy";
    public static String SETUP_COMMAND = "setup";
    private String discordBotToken;
    private final KafkaTemplate<String, Serializable> kafkaTemplate;
    private JDA jda;
    @SneakyThrows
    @PostConstruct
    @Override
    public void startConnection() {
        log.info("====================================================HEY HEY IAM HERE===================================================");
        Dotenv load = Dotenv.configure()
                .directory("./.././")
                .load();
        discordBotToken = load.get("DISCORD_BOT_TOKEN");
        jda = JDABuilder.createDefault(discordBotToken)
                .addEventListeners(this)
                .setActivity(Activity.listening("your truly desires"))
                .enableIntents(GatewayIntent.DIRECT_MESSAGES,GatewayIntent.DIRECT_MESSAGE_TYPING)
                .build();
        jda.awaitReady();

//        CommandData buddy = new CommandDataImpl("buddy", "i will save you with my guide my friend!")
//                .addOptions(new OptionData(OptionType.STRING, "userId","user who needs my help", true)
//                        , new OptionData(OptionType.STRING, "msg","give me the introduction on how i can help your friend", true));
//        CommandData gptKey = new CommandDataImpl("setup", "Give me the power of unlimited MAGIC (also my gptAPI key)")
//                .addOptions(new OptionData(OptionType.STRING,"gptKey","my gpt key", true));

        jda.updateCommands().addCommands(Commands.slash(BUDDY_COMMAND,"i will save you with my guide my friend!")
                .addOptions(new OptionData(OptionType.STRING, USER_ID_COMMAND,"user who needs my help", true)
                        , new OptionData(OptionType.STRING, MESSAGE_COMMAND,"give me the introduction on how i can help your friend", true)),
                Commands.slash(SETUP_COMMAND, "Give me the power of unlimited MAGIC (also my gptAPI key)")
                        .addOptions(new OptionData(OptionType.STRING,GPT_KEY_COMMAND,"my gpt key", true))).queue();
    }



    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
//
        String message = event.getMessage().getContentDisplay();
        log.info("MY BOT IS RECEIVING MESSAGEES=====>{}",message);
        User author = event.getAuthor();

        NeuralProcessRequest neuralProcessRequest = new NeuralProcessRequest();
        neuralProcessRequest.setMessage(message);
        neuralProcessRequest.setUserId(author.getId());
        kafkaTemplate.send("conversation-topic",neuralProcessRequest);
    }

    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        log.info("MY BOT IS RECEIVING EVENTS=====>{}",event.toString());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals(BUDDY_COMMAND)) {
            buddyCommand(event);
       if(event.getName().equals(SETUP_COMMAND)){
           setupCommand(event);
       }
    }
    }

    private void buddyCommand(SlashCommandInteractionEvent event){
        try{
            String userId = event.getOption(USER_ID_COMMAND).getAsString();
            String startMessage = event.getOption(MESSAGE_COMMAND).getAsString();
            User userById = jda.getUserById(userId);
            if(userById.getId()!= null){
                NeuralProcessRequest neuralProcessRequest = new NeuralProcessRequest();
                neuralProcessRequest.setMessage(startMessage);
                neuralProcessRequest.setUserId(userById.getId());
                kafkaTemplate.send("conversation-topic",neuralProcessRequest);
                event.reply("entendo e entrarei em contato!").queue();
            }
        }catch (Exception e){
            event.reply("não encontrei esse usuario").queue();
            log.error(e);
        }

    }

    private void setupCommand(SlashCommandInteractionEvent event){
        String gptKey = event.getOption(GPT_KEY_COMMAND).getAsString();
        log.info("RECEIVED GPT CHANGE REQUEST WITH KEY=> {}", gptKey);
    }

    @Override
    @NeuralConsumerCustomListener
    public String consumeBestMatch(@Payload NeuralProcessResponse neuralProcessResponse) {
        String message =neuralProcessResponse.getMessage();
        log.info("RECEIVED IN CONVERSATION::::: {}",message);
        User user = this.jda.getUserById(neuralProcessResponse.getUserId());
        PrivateChannel channel = user.openPrivateChannel().complete();
        Consumer<Message> callback = (response) -> log.info("Sent Message {}", response);
        channel.sendMessage(message).queue(callback);
//                .queue(privateChannel -> privateChannel.sendMessage(message));

        return message;
    }


    @Override
    public void endConnection() {

    }
}
