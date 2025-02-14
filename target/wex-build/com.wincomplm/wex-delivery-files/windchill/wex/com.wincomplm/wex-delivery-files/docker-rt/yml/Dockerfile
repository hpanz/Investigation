# Use the official code-server image
FROM codercom/code-server:latest

# Switch to root user to install packages
USER root


# Install required tools
RUN apt-get update && apt-get install -y curl unzip maven && \
    curl -fsSL https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz -o /tmp/java.tar.gz && \
    mkdir -p /usr/lib/jvm/jdk-21 && \
    tar -xzf /tmp/java.tar.gz --strip-components=1 -C /usr/lib/jvm/jdk-21 && \
    rm /tmp/java.tar.gz

# Force update Java alternatives (override OpenJDK 17)
RUN update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-21/bin/java 100 && \
    update-alternatives --set java /usr/lib/jvm/jdk-21/bin/java

# Set JAVA_HOME to Java 21
ENV JAVA_HOME=/usr/lib/jvm/jdk-21
ENV PATH="$JAVA_HOME/bin:$PATH"

# Set MAVEN_OPTS
ENV MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED"

# Switch back to the default coder user
USER coder
