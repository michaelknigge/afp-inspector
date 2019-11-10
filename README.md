# afp-inspector [![Build Status](https://travis-ci.org/michaelknigge/afp-inspector.svg?branch=master)](https://travis-ci.org/michaelknigge/afp-inspector)

JavaFX application for analysis of [AFP](https://en.wikipedia.org/wiki/Advanced_Function_Presentation) ([MO:DCA](https://en.wikipedia.org/wiki/MODCA)) files.

**NOTE: This project started in November 2019 and is completely useless in its current state. Keep in mind that this is a hobbyist project. So I guess
it will take up to two years to make afp-inspector an application that is of any use.**

# Contribute
If you want to contribute to afp-inspector, you're welcome. But please make sure that your changes keep the quality of afp-inspector at least at it's current level. So please make sure that your contributions comply with the afp-inspector coding conventions (formatting etc.) and that your contributions are validated by JUnit tests.

It is easy to check this - just build the source with `gradle` before creating a pull request. The gradle default tasks will run [checkstyle](http://checkstyle.sourceforge.net/), [findbugs](http://findbugs.sourceforge.net/) and build the JavaDoc. If everything goes well, you're welcome to create a pull request.

Hint: If you use [Eclipse](https://eclipse.org/) as your IDE, you can simply run `gradle eclipse` to create the Eclipse project files. Furthermore you can import Eclipse formatter settings (see file `config/eclipse-formatter.xml`) as well as Eclipse preferences (see file `config/eclipse-preferences.epf`) that will assist you in formatting the afp-inspector source code according the used coding conventions (no tabs, UTF-8 encoding, indent by 4 spaces, no line longer than 120 characters, etc.).
