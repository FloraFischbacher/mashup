# <img alt="Mashup" src="https://file.garden/ZX3HQS8pMXxJ-59i/mashup.png" width=500 />

> [!WARNING]
> Mashup is **pre-alpha software**; for now, use of Mashup over the official mod system for Cassette Beasts is **not recommended and unsupported**! Core aspects of its feature-list are not implemented, and many of the features that *are* implemented could have serious issues—if you experience any issues while using Mashup, [please report them here](https://github.com/FloraFischbacher/mashup/issues).

## Overview

> [!IMPORTANT]
> This README describes aspects of Mashup which may be more *prospective* than *descriptive* as it pertains to the current state of the codebase—this was written as a primer to the design goals of Mashup in general.

Mashup is an alternative mod-loader for the game *[Cassette Beasts]()*, designed to surpass the officially-supported system in terms of capabilities and developer ease-of-use. At a lower level, it uses [GDRE Tools]() as a way of automatically patching the game's internals prior to mod-loading (without the headache that doing so manually would cause), allowing Mashup to work 100% independently from the game's process. It features a [declarative patch language]() for making changes to Cassette Beasts' internals, a [robust dependency resolution system]() which is granular at the level of patches (*not just mods*), and a friendly [graphical]() and [command-line]() interface for users and developers alike to start making and using mods for the game.

Mashup is lovingly documented for *both* the perspective of newcomers to modding video games in general as well as seasoned developers looking to get quickly started. I fundamentally believe that if you have an idea you want to see in the game, you should be able to quickly and effectively realize it, and I will personally help out in whatever way I can. :)

## Why Mashup?

While the officially-supported system for modding the game is more than enough for a lot of purposes (evidenced by the fledgling community of modders creating genuinely impressive mods for the game, such as [Living World]() and [False Wirral](), just to name a couple), it has some serious drawbacks that make most tasks require workarounds or questionable practices for future mod compatibility.

Without [getting into the technical details]() as to why, a vast majority of the files of Cassette Beasts are unable to be modified, and those that are able to be modified are subject to a vast array of restrictions on the exact manner in which they can be edited. This has led the modding community for the game to devise a myriad of clever "hacks" that vary in their effectiveness depending on the circumstance.

Mashup was originally started due to these temporary solutions failing to work well enough (code smell notwithstanding) for the scope and featureset of a modding project I and my creative partner had worked on for months by the time it developed as an idea. It proved to be far more productive in the long run to start from scratch with a brand-new mod-loader than to continue to push forwards with a system where the limitations were actively stalling the development process for the mod to a halt.

In comparison, I aim to design Mashup to be a sound, stable foundation where these workarounds are not needed.

## What's the catch?

This isn't to say that Mashup is without an upfront cost—a system with greater capabilities almost always implies a system with at least some amount of increased complexity, and this project is not an exception to that guideline. While the documentation aims to make the process of converting mods between the official mod-loader and Mashup as simple as possible, it is **extremely likely** that major differences will exist between the two *by design*.

Mashup being completely independent from Cassette Beasts in functionality means the loss of the integrations Cassette Beasts has with things like the Steam Workshop, as potential mod players will need to install a separate program with its own launcher in order to use those mods. While this is equivalent in status to existing mod-loaders for other games such as *Minecraft* (with [NeoForge]() and [Fabric]()) and *Celeste* (with [Everest]()), it is an additional barrier to entry that is unfortunately inevitable.

Finally, due to the way Mashup handles mod-loading outside of the game, Cassette Beasts mods loaded through the official mod-loader **may severely break Mashup mods** due to them loading as the game loads and potentially replacing resources that Mashup mods manipulate and use. Because of this, Mashup disables code relating to the vanilla game's mod system by default (*though this is configurable, if you prefer to live on the edge*).