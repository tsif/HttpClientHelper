HttpClientHelper
================

An Android java library for asynchronously making http requests and notifying observers of the response and its contents.
It makes use of AsyncTask and Observer.

## Sample usage

To use this library you can either export a .jar file and add it to your project or import the project into Eclipse and access the library that way.

The steps to take:

You can check out the example package in this repo for how to create your own asynchronous requests. It uses the github API to get the list of user tsif's repos and also the imgur API to upload an image there and also keep track of the upload progress.

First you sublass HttpClientManager and override parseResult, successNotification, failNotification and badNotification. 
The you create a method that will execute your request attributes with parameters such as url, headers etc.
In you client code, an Activity for instance, implement Observer and override the update method. Enjoy!


## Version

0.2

## Contributing

- Fork it
- Clone it
- Edit it on a feature branch
- Commit it
- Push it to your origin
- Create a pull request

## Authors

* [@tsif][tsif]

## Release History

## License

Licensed under the [MIT License](LICENSE-MIT)

[tsif]: https://github.com/tsif
