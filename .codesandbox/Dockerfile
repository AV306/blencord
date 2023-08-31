FROM debian
RUN apt update -y
RUN curl -s "https://get.sdkman.io" | bash
RUN source "$HOME/.sdkman/bin/sdkman-init.sh"
RUN sdk install gradle && sdk install java 17.0.7-zulu

