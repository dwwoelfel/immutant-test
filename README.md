Sample project to demonstrate a problem with immutant's on-close handler not being fired on Safari.

## Setup

Run `lein run`. A server should start and print out the port that the server is running on. It will print to the console when there is an event involving a connection.

In Safari, browse to [http://localhost:23142/](http://localhost:23142/) and refresh a few times. You'll see a table of the connections, which should refresh whenever the connections change.

Notice that the channels close, but the on-close an on-error handlers aren't fired.

You may have to force-quit Safari to get the desired effect.

## More info

We see this in the wild on [https://precursorapp.com](https://precursorapp.com). Here are some of the user agents that we've seen with this problem:

"Mozilla/5.0 (Linux; Android 4.4.4; SM-G900V Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.93 Mobile Safari/537.36"

"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36"

"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/600.6.3 (KHTML, like Gecko) Version/8.0.6 Safari/600.6.3"

"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/40.0.2214.111 Chrome/40.0.2214.111 Safari/537.36"

"Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko"

"Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12F70 GroupMe"

"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/600.5.17 (KHTML, like Gecko) Version/8.0.5 Safari/600.5.17"

"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36"

"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/600.4.10 (KHTML, like Gecko) Version/8.0.4 Safari/600.4.10"
