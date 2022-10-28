# Hyperskill_Android_Stopwatch

### Stage 5/5: Friendly reminder

The reminder I was instructed to make is anything but friendly. The insistence flag is SO annoying. I would not wish it upon my worst enemies.
Honestly I thought at first that i'd done something horribly wrong (and maybe I have). But after removing/adding the flag, I think most of the frustration comes from that one flag. \

All in all, I'm still trying to come to grasp just how everything is supposed to come together. This was a nice little trial in that, but I'd like to make something else, something more with my own vision in mind so that I can properly understand things. \
Oh, and I know that a lot of the code is still sloppy/text is placeholder. Just trying to get something working not something pretty. At least right now.

Ok. Bye!

### Stage 4/5: Custom time limit

This was fun, this was fine. I don't remember if I had much trouble with it.

### Stage 3/5: Progress bar

I absolutely despised this. What a terrible pain in the butt. Adding a progress bar? Easy. Simple. Super straightforward. \
Somehow, though. Making it change color every second was traversing a labyrinth. The default minSDK for this project is 20, which doesn't support `setIndeterminateTintList()`
So, I went about trying to change color some other way, of which there are many, it seems! And the method of which to do so has changed over the years, mostly between sdk 20 and 23
So while searching for answer for "the best way to change a color programmatically." I ran into answers that either didn't work or were considered deprecated by the compiler or not supported by sdk21 \
When I finally found something that was neither deprecated nor unsupported, the built-in edutools check failed. Why?

Because it was checking for the `progressBar.indeterminateTintList?.defaultColor` property. So I had to use setIndeterminateTintList after all, and just change the gradle.build.
Really frustrating to have to run around like that.

I understand at the end of the day that "this is android development, get used to it pal." But gosh golly does that just put me in a way. \ 
GRUMPY FACE

Anywho, it's done now. I'm feeling relieved. Onto other things. \
Ciao

### Stage 2/5: Add functionality

Woo! Okay, buttons work and do stuff. I'm still only vaguely familiar with threads, but this was a nice way to dip my toes in. \
The stage description mentioned that you could use `android.os.CountDownTimer` to do this, but I wanted to avoid that if possible?
I guess I figured "doing things the hard way" would help familiarize me with handlers/threads. I copied a chunk of code from a previous lesson on handlers.
the `private val updateTime: Runnable = object : Runnable {
override fun run() {` bit.
But I wanted to be able to reference the `binding.textView` from within the runnable. I tried changing it from a `val` to a `fun` but that caused problems when I tried to have the reset button `handler.removeCallbacks(updateTime(binding.textView))`
I realized later that I could just. use `binding.textView` directly inside the `updateTime` runnable, because "that's how bindings work, silly!"

So that's nice. :) \
I still don't know I totally understand everything, but I got stuff working, and I know some things that DON'T work. So

Onwards and upwards!

### Stage 1/5: Stopwatch setup

I'm surprised the course asked me to learn about Handlers and Activities without yet implementing them. I won't complain, it's nice to know. \
I'm just really looking forward to actually using the things I've learned.