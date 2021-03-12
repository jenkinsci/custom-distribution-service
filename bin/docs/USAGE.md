
## Overview 

The custom distribution service currently has three sections in it navigation bar:  

* Plugins : This section provides the entire list of 1700 plugins for the user to choose from. It also includes filters and a search bar to assist in the search of plugins.

* Package Generation: This section contains the editor and two seperate services :
    
    * Download War File
    * Download Package Configuration 

* Community Configurations: This section displays the list of public configurations developed and shared by the community.

## Who is this tool for

This tool has been primarily designed for:

    a) Users who want to generate custom packages.
    b) Users who want an out-of-the-box jenkins war file ready to use.
    c) Users who use popular configurations like AWS or blueocean and want to use community generated configurations frequently.

## How to use 

This guide will take you through the steps of creating and downloading a custom war package.

### Step 1 : Choosing plugins

a) Click on the plugins section of the home page of the custom distribution service.
You will see a set of plugins on the page.


<img src="../images/plugins_page.png" alt="Plugin page image" />

b) Choose the plguins by clicking on the `Add to Configuration` button.
 Additionally you can search for the plugins you desire using the search bar on the top or the `Filters` on the top left of the screen.

c) After you have selected the plugins click on the `Submit Plugins` on the bottom of the page to proceed with the generation of the package.


### Step 2: Entering package configuration details.

a) After completing the above step you will see a modal screen similar to the one below

<img src="../images/package_generation.png" alt="Package generation page" />

b) The fields in that list are self-explanatory and can be entered according to the requirements.

c) After you have entered that information click on the `Generate Package` button at the bottom of the modal.

d) This will kick-off the generation of the custom package and once done will take you to the editor on the next screen.

### Step 4: Downloading the packages

a) After the completion of the previous step you will see a configuration on the editor similar to the structure below depending on the configuration you have selected.

<img src="../images/editor.png" alt="Editor page" />

b) There are two buttons on the left namely

1) Download Package: This feature downloads the contents of the editor into a yml file with the name `casc.yml`

2) Download War: This feature generates a war file and can be found with the name `jenkins.war`


## Community Configuration Section

This section provides the user with public community configurations that one can view. To view and download the configurations.


<img src="../images/community_config.png" alt="Community Config page" />

a) Click on the `Community Configurations` tab on the navigation bar.

b) Click on the `View` button on any of the individual cards you want to see.

c) You will be redirected to an editor screen similar to `Step 4` above and you can follow the same steps to download the package or war file.

** Note: It could be that these community configurations may have placeholders like {{Jenkins.version}}. Please make sure that you enter the correct version here, so that it can generate the war package correctly.


## Generating your own WAR file

If you do not want to use the package generation feature of the service to generate a packager configuration file. You can paste your own configuration into the editor and generate and download the war file.
