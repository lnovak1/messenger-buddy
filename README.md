# messenger-buddy

Bem vindos a uma integração entre a API do chatGPT e um bot do Discord

## Pré-requisitos

para rodar esse projeto em sua máquina, é necessárrio algumas dependências e pré requisitos:

- JAVA 17 (https://www.oracle.com/java/technologies/downloads/#java17)
- MVN(https://maven.apache.org/download.cgi)
- Docker(https://www.docker.com/)
- Node.js(https://nodejs.org)
- Chave API Discord (https://discord.com/developers/applications)
- Chave API GPT (https://platform.openai.com/account/api-keys)

## Como Configurar e Iniciar a aplicação

1. Clonar o repositório do Git
2. Após clonar o projeto encontre o arquivo DiscordBot.java e altere o campo discordBotToken = "INSERT YOUR DISCORD KEY HERE"; com sua chave obtida do bot Discord
3. A partir de agora todos os comandos estão presentes no package.json, começe subindo o container onde está o kafka. com o comando **npm run kafka-init**
4. agora é hora de compilar os 2 projetos, para isso rode **npm run build**
5. e por final para iniciar o projeto utilize **npm run start**

## Uso

Uma vez que a aplicação esteja rodando, é necessário autorizar o bot em seu servidor , para isso utilize o link: 
<br> https://discord.com/oauth2/authorize?client_id=<YOUR_DISCORD_CLIENT_ID>&scope=bot&permissions=8 <br>
onde <YOUR_DISCORD_CLIENT_ID> é o client id da sua aplicação do discord , e pode ser obtido aqui : <br> https://discord.com/developers/applications <br>

<br> É importante lembrar que o bot só consegue conversar com pessoas cujo as quais compartilhem pelo menos um discord com o bot.<br>

Uma vez no mesmo servidor utilize o comando  <br> **/setup** **<YOUR_GPTKEY>** passando sua chave da api do GPT <br>

Pronto , agora é só conversar com ele ou usar o comando **/buddy** **<FRIEND_ID>**  **MESSAGE**  para ele começar a conversar com um amigo seu ,
lembre que esse amigo deve compartilhar ao menos um servidor com o Bot

<br> É possível verificar os tópicos de conversação do kafka pelo Kafdrop em : http://localhost:19000/

## Licença

Este projeto está licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT).

A Licença MIT é uma licença de software permissiva que permite que as pessoas façam o que quiserem com o seu código, desde que forneçam atribuição e não responsabilizem você por problemas decorrentes do uso do software.

Para mais informações, consulte o arquivo [LICENSE](LICENSE).
