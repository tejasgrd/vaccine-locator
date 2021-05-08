# Vaccine Locator
### Humble Request: Please do not overwhelm the cowin server, keep the ping duration  optimum, by default its hard coded to 20 Minutes


Vaccine Locator is service tha calls the cowin(co-vin) API and fetches the available slots (18+ only) for vaccine for current day only. if slots are available this service will send message on Configured Public Telegram channel. 

This application as of now fetches vaccine slots for only 1 configured district. Pull request's are open. If you are running the application for your district please update the README file here as well as application.yml so that if for your district anyone else is running application you don't have to run it just join telegram channel.

## Configuration Required in application.yml file

- District ID along with name in districts: all:  
- Name of the district for which ID has been configured under cowin:
- Telegram API Key in telegram: tApiKey: 
- Telegram channel name under telegram: tChannelName: 

## Telegram Channel Configuration
Please follow [link 1] and [link 2] to create Telegram channel and get the API token from BotFather.
Use the API key as mentioned above in application.yml file and Channel name from channel description (not the actual name)

## Running Application

Once above configuration is done, run the below commands
```sh
mvn clean install
mvn spring-boot:run
```
## List of Telegram channel
| District      | Telegram Channel |
| ------------- | ---------------- |
| Pune          | vcpunedistrict   |

## License

MIT

[link 1]: https://xabaras.medium.com/sending-a-message-to-a-telegram-channel-the-easy-way-eb0a0b32968
[link 2]: https://rieckpil.de/howto-send-telegram-bot-notifications-with-java/
