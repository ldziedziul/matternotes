# Matternotes
### Description
Matternotes is a Java based tool for creating and managing private notes in the [Mattermost](Mattermost.org).
You can create notes for the specific channel or globally accessible titled notes. Notes are always visible only for their creator.
Matternotes was inspired by the [/notes](https://www.slashnotes.com) for Slack. In contrast to [/notes](https://www.slashnotes.com) Matternotes is hosted on your server and you have full control of your data.

### Prerequisites
Java 8

## Configuration
See [src/main/resources/application.properties](src/main/resources/application.properties) for the details. Database with notes (H2 engine) is stored in `~/matternotes`.
You can change `application.properties` in sources or override properties with optional `spring.config.location` parameter. See [Running](#running) section for examples
#### Integration with Mattermost
Go to Integrations from [Mattermost](Mattermost.org) menu and [add a new Slash Command](https://docs.mattermost.com/developer/slash-commands.html#set-up-a-custom-command) with following options:
* Display Name: `/notes`
* Command Trigger Word: `notes`
* Request URL: `http:/localhost:8089/notes` (default value if Matternotes runs on the same machine as [Mattermost](Mattermost.org))
* Request Method: `POST`
* Response Username: `Notes`

## Running
##### Option 1 - run with the gradle task

```
./gradlew bootRun -Dspring.config.location=file:/path/to/config/matternotes.properties
```

##### Option 2 - build the jar

```
$ ./gradlew build
$ java -jar build/libs/matternotes-0.0.1-SNAPSHOT.jar --spring.config.location=file:/path/to/config/matternotes.properties
```


###Usage
Type `/notes` or `/notes help` to see help:

Command | Description
------ | ------
/notes my private note | Creates 'my private note' as your note for the current channel/conversation. If one already exists, it will append it to your note
/notes [title] my private note | Creates 'my private note' under a 'title' notepad. If 'title' already exists, it will append it to your note
/notes show | Shows your notes for the current channel/conversation
/notes show [title] | Shows your notes with the specified title. Square brackets are necessary and not part of the name
/notes show all | Show all your notes, grouped by channel/conversation
/notes clear | Clears permanently all your notes for the current channel/conversation.
/notes clear [title] | Clears permanently all your notes with the specified title.
/notes undo | Undo your latest change for the note for the current channel, i.e. your latest appended message will be deleted
/notes undo [title] | Undo your latest change for the note with the specified title, i.e. your latest appended message will be deleted
/notes help | Displays this list of commands
/notes about | Shows some information about Matternotes
