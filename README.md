# finatra-quill-protobuf
Demo CRUD using Scala Famework (Finatra), Quill (FRM), Protobuf (Protocol Buffer) and MySQL Database

FORMAT: 1A
HOST: http://127.0.0.1/v1

# Hendi Comment Microservice

This document outlines the design and architecture of the user comments service. 

The comments user service is a component in Hendi service oriented architecture stack.  

It encapsulates and provides a suite of comments service endpoints to allow other components or services to query and update Hendi comments user data.

# Group Comment

## Comments Resource [/comments] 

### List All Comments [GET /comments{?off}{?lim}{?src}{?art}{?stsDel}{?stsAct}]

You may list all comments using this action. It takes a JSON
object containing a comment in the form of choices.
By default it will list All comments by created date sorted by descending.


+ Parameters
    + off: 0 (number, optional) - Page Number (default = 0)
    + lim: 3 (number, optional) - Item Number (default = 10)
    + src: `anarkis` (string, optional) - Keyword to Search (default = '')
    + art: 123 (number, optional) - Article ID which comments belong to
    + stsDel: 0 (enum[number], optional) - Delete status of comments
        + Members
            + `0`
            + `1`
    + stsAct: 0 (enum[number], optional) - Active status of comments
        + Members
            + `0`
            + `1`

+ Response 200 (application/json)
    + Attributes (CommentResponseAll)

### Create a New Comment [PUT]

You may create your own comment using this action. It takes a JSON
object containing a comment in the form of choices.

+ Request (application/json)
    + Attributes (BaseComment)

+ Response 204 (application/json)
    + Attributes (CommentResponse)

### Delete a Comment by Id [DELETE /comments/{Id}]

You may delete your own comment using this action (by id). It takes a JSON
object containing a comment in the form of choices.

+ Parameter
    + Id: 212 (number) - Comment ID

+ Response 204 (application/json)

### Bulk Delete Comments [DELETE]

You may bulk delete your own comment using this action (many comment id's). It takes a JSON
object containing a comment in the form of choices.

+ Request (application/json)
     + Attributes
        + ids (array[number]) - comments indices
            + Members
                + `1`
                + `3`
                + `5`
            

+ Response 204 (application/json)

### Activate a Comment by Id [POST /activate/{Id}]

You may activate your own comment using this action (by id). It takes a JSON
object containing a comment in the form of choices.

+ Parameter
    + Id: 212 (number) - Comment ID

+ Response 204 (application/json)

### Bulk Activate Comments [POST /activate]

You may bulk activate your own comment using this action (many comment id's). It takes a JSON
object containing a comment in the form of choices.

+ Request (application/json)
     + Attributes
        + ids (array[number]) - comments indices
            + Members
                + `1`
                + `3`
                + `5`

+ Response 204 (application/json)

### De-Activate a Comment by Id [POST /deactivate/{Id}]

You may activate your own comment using this action (by id). It takes a JSON
object containing a comment in the form of choices.

+ Parameter
    + Id: 212 (number) - Comment ID

+ Response 204 (application/json)

### Bulk De-Activate Comments [POST /deactivate]

You may bulk activate your own comment using this action (many comment id's). It takes a JSON
object containing a comment in the form of choices.

+ Request (application/json)
     + Attributes
        + ids (array[number]) - comments indices
            + Members
                + `1`
                + `3`
                + `5`

+ Response 204 (application/json)






# Data Structures

## BaseComment (object)
+ articleId: `1259973` (number, required) - Article ID
+ replyTo: `0` (number, required) - Reply To ID
+ userId: `150` (number, required) -  User ID
+ username: `hendisantika` (string, required) - Username
+ email: `hendisantika@yahoo.co.id` (string, required) - Email
+ content: `Demo 411 merupakan demo yang bermartabat. Maka kita jangan sekali2 mengangpnya sebagai demo anti-rasis atau SARA. Apalagi berpendapat bahwa demo itu ditunggangi oleh actor politik. Gak ada penipuan` (string, required) - Content
+ created: `1479978161` (number, required) - Created Date / Epoch Unix Stamp
+ report: `0` (number, required)
+ isDeleted: `0` (number, required) - Deleted notice
+ isActive: `0` (number, required) - Status Comment

## Comment (BaseComment)
+ Id: `212` (number, required) - Comment ID

## CommentResponse (object)
+ Id: `212` (number, required) - Comment ID

## CommentResponseAll (object)
+ `comments` (array[Comment], optional) - Comment indices on 128 cluster.

## BulkCommentRequest (object)
+ `ids` (array[number]) - Comment indices on 128 cluster.

