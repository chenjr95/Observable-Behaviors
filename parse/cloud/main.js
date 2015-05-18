
Parse.Cloud.define("sendEmail", function(request, response) {
  var Mandrill = require('mandrill');
  var recipient = request.params.recipientName;
  var recipientEmail = request.params.recipientEmail;
  var subjectLine = request.params.subject;
  var messageText = request.params.message;
  Mandrill.initialize('kpmEIzTy1LHYrqkUxjzerA');
  Mandrill.sendEmail({
      message: {
          text: messageText,
          subject: subjectLine,
          from_email: "cis350observablebehavior@gmail.com",
          from_name: "Observable Behaviors App",
          to: [
          {
              email: recipientEmail,
              name: recipient
          }
          ]
      },
      async: true
  },{
      success: function(httpResponse) {
          console.log(httpResponse);
          response.success("Email sent!");
      },
      error: function(httpResponse) {
          console.error(httpResponse);
          response.error("Uh oh, something went wrong");
      }
  });
});
