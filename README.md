# LoLAid
A league of legends aid tool. 

Currently looks at montitor resolution and as of 5.14 the MiniMap Scale Factor to detected when a champion's ult is ready and announces it via sound by using Text To Speach Conversion of current Champion names.

It notifies you via sound so you don't have to look while playing!
It auto detects your resolution and all you have to do is set the Minimap scale value to match your in game Minimap scale value.

I'm hoping that users especially those that forget to check will find this useful in that they will be reminded! Maybe even make people better as they become more aware of team ults to better know when to go in to fight.

Also I have added custom audio files for each champ! So once all champs are selected at the start of match, you can go in and set the champ names.

Champ 1 is the furthest right champ and Champ 4 is the left most champ (4 3 2 1) as displayed on the right. 

You'll need the Java Run Time Environment installed if you don't have it already. 

As of 5.14 Riot finally added a cool down animation for ally ults. I decided to keep my counters so that you can see numbers on top of the animation provided by riot.

The timer counters work by waiting for an allied champions ult to come up. Once it does and they use it for the first time, the program counts up waiting for the ult to come back up again, once the ult comes up again it remembers what it counted and uses that as that champions own cool down timer! 

So long as their ult cool down keeps matching what the program thinks it should be. it keeps it. If they start buying cool down items or they level up their ult then it will just reprogram it. 
