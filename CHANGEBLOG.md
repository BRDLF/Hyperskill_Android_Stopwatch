# Hyperskill_Android_Stopwatch

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