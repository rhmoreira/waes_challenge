# WAES - Base64 comparator challenge project
#### This is a project for the [WAES](https://www.wearewaes.com/) Selective process.


## Requirements
Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints
```
<host>/v1/diff/<ID>/left 
<host>/v1/diff/<ID>/right
```
The provided data needs to be diff-ed and the results shall be available on a third endpoint
```
<host>/v1/diff/<ID>
```
The results shall provide the following info in JSON format
* If equal return that
* If not of equal size just return that
* If of same size provide insight in where the diffs are, actual diffs are not needed.
  * So mainly offsets + length in the data

Make assumptions in the implementation explicit, choices are good but need to be communicated 

## Must haves

* Solution written in Java
* Internal logic shall be under unit test
* Functionality shall be under integration test
* Documentation in code
* Clear and to the point readme on usage

# The Project
