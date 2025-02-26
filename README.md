Lieutenant
==========
Lieutenant is a small (almost too small to be a separate library to be frank) library making it easy to
create and register simple platform independent brigadier commands.

## Why?
More and more Minecraft modding platforms are embracing Brigadier as their official, recommended command layer.
(Neo)forge and Fabric has used Brigadier since it's introduction due to their nature, Velocity has always used
Brigadier as it's command layer, Sponge builds a custom command layer on top of Brigadier but the implementations
also allow registering Brigadier commands directly and Paper has experimental, but very well functioning, support as well.

However, each platform uses their own command source type and as such cross-platform modifications encounter trouble
trying to use Brigadier for their commands, while a majority of commands only require running code in response to a
command call and sending feedback to the caller this functionality either has to be implemented in a cross-platform
custom format and translated to the platform specific command systems or has to be implemented independently for each
platform. Lieutenant aims to let modification authors implement these simple commands in a cross-platform manner and
let Lieutenant handle translation to platform native commands.

## Usage
Using Lieutenant starts in the cross-platform module of a project. First bring `lieutenant-core` in as a dependency
from `https://maven.nelind.dk/releases`. In the project core you want to build out your commands using Lieutenants
command source:
```java
public class TestCommand {
    public static LiteralArgumentBuilder<LieutenantSource> createTestCommand() {
        return Lieutenant.literal("testCommand")
            .requires(source -> source.hasPermission("example.command.test"))
            .executes(ctx -> {
                var source = ctx.getSource();
                source.sendMessage(Component.text("Example test command ran!"));

                return Command.SINGLE_SUCCESS;
            });
    }
}
```

In your platform implementations you then want to depend on the Lieutenant module for the platform in question
(for example `lieutenant-paper` on Paper) and use Lieutenants helper to register commands. Each helper will return the
command in the format most appropriate for the platform. For example `PaperLieutenant::toPaperCommand` returns a
`LiteralCommandNode<CommandSourceStack>` whereas `VelocityLieutenant::toVeloctiyCommand` returns a `BrigadierCommand`
