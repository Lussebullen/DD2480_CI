# DD2480_DECIDE

## Description
This repository contains the files of the DD2480 DECIDE task for group 17. The purpose of this repo is to act as a dummy repo for testing the CI server the group creates.

## Requirements
Java version: 8+

JDK version: 8+

Maven version: 3.8+

## Build instructions
To use Maven to test this repo, navigate to ./decide/ and run ```mvn test```

### Linked issues
All initial commits to this branch are linked to issue #1.

<details>
<summary>DECIDE task README></summary>
 
## Project description
This repository includes a program that solves the "decide" problem, as stated in the DECIDE Assignment in the KTH course DD2480. The "decide" problem is part of the *Launch Interceptor Program: Requirements Specification*, a hypothetical anti-ballistic missle system that generates a boolean signal to launch a hypothetical missle depending on radar tracking information provided as input.  

## Requirements
Java version: 8+

JDK version: 8+

Maven version: 3.8+

## Build instructions Windows
Install chocolatey https://docs.chocolatey.org/en-us/choco/setup

Install Maven via chocolatey, `~ choco install maven`

## Build instructions MacOS
Install brew `~ /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"`

Install Maven via brew `brew install maven` 

## Build instructions Linux
Install `maven` using the package manager included in your linux distro, for example:
* On Arch use `pacman -Syu maven`
* On Fedora use `dnf install maven`
* On Ubuntu use `apt-get maven`
* On Debian use `apt` 

### To run in command line:
Navigate to the directory with the pom.xml file, for this project navigate to `DD2480_DECIDE\decide`(on Windows) or `DD2480_DECIDE/decide` (on Unix-systems).

Compile `mvn compile`

To run tests, execute `mvn test`

To run the program, execute `mvn exec:java -Dexec.mainClass="group17.App"`

### To run in visual studio code:
Install recommended java plugins, including Maven

Open DD2480_DECIDE as work folder

In the bottom left, at the maven tab, open the "Lifecycle" tab

Hover any option of your choice, such as "test", and click the play button to run the corresponding option

If running Windows, if vscode says the maven executable is missing and requests it, insert the following `C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.6\bin\mvn` if you used the default install location of Maven from chocolatey

## Commit convention
All commits should be performed on the appropriate issue branch

Prefixes: feat/, fix/, doc/, refactor/
 
## Statement of Contributions
- ##### [Adam Siraj](https://github.com/asirago)
I worked on the implementation and unit testing of LIC 2, 7 and 12. I also added the GitHub Actions workflow to build and run the maven project, as well as the accompanying unit tests. I also implemented a discord bot and webhooked it to GitHub to receive notifications every time a pull request was created.

- ##### [Emil Sjölander](https://github.com/emilsjol)
I implemented LIC3 and LIC8, as well as accompanying unit tests. I performed a lot of code review of pull requests. During this task, I had an administrative role, scheduling upcoming meetings as well as performing administrative work on GitHub, such as labeling and creating issues, and managing tasks. Additionally, I worked to maintain a good pace during meetings.

- ##### [Emir Catir](https://github.com/empazi)

Implemented LIC 4, 9 and 14. Implemented corresponding unit tests for the relevant LICs. Helped with pull request code reviews.

- ##### [Omar Askar Vergara](https://github.com/Omar-AV)
Implemented the decide function in `App.java`, LIC 0, 5, 10, 13 and corresponding unit tests for the LICs. Performed pull request reviews. Worked on 3 main cases for decide.

- ##### [Mathias Duedahl](https://github.com/Lussebullen)
Implemented LIC 1, 6, 11 and corresponding unit tests for the LICs. Performed pull request reviews early in the project. Initially I took part in setting up work structure with suggested skeleton code framework and Kanban board for delegating work. Contributed to setting up local build and testing using Maven and Make.

## Remarkable Achievement
We are proud that we have set up a GitHub Actions workflow with automated unit testing. Each. It has greatly contributed in continuous integration by automatically catch unforseen errors and bugs that gets introduced each pull request. 

- Set up Repo with branch protection and Project with assignment requirements added

## Essence analysis
The team has established a way-of-working and all team members support and use the agreed-upon setup. The direction and requirements of the work are clear, and the team is currently working well. However, though the communication channels are in place, the whole procedure is rather new to all team members, and thus “Working Well” feels a bit forced as we are still finding out tool-specific techniques. It's also hard to determine whether we are truly done with the ‘In Use’ requirement of “The use of the practices and tools selected are regularly inspected” as we are not fully comfortable with all tools but regularly inform each other of newly found techniques and such. 

To reach the Working Well-state some more familiarity is needed with the tools, so that operations can run smoother. When the whole team achieves a comfortable level with our agreed upon framework (including familiarity with all tools), we would move on to “Working Well”.
</details>

Test CI server #3
