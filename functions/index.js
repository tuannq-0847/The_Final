'use strict'

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


exports.sendNotification = functions.database.ref('/NOTIFICATION/{receiver_user_id}/{notification_id}')
.onWrite((data, context) =>
{
	const receiver_user_id = context.params.receiver_user_id;
	const notification_id = context.params.notification_id;

	const DeviceToken = admin.database().ref(`/USER/${receiver_user_id}/deviceToken`).once('value');

	return DeviceToken.then(result => 
	{
		const token_id = result.val();
		const data = admin.database().ref(`/NOTIFICATION/${receiver_user_id}/${notification_id}/contents`).once('value')
		data.then(res => {
			const from = admin.database().ref(`/NOTIFICATION/${receiver_user_id}/${notification_id}/from`).once('value')
			from.then(from_id => {
				admin.database().ref(`/USER/${from_id.val()}/userName`).once('value')
				.then(username =>{
					const payload = 
					{
						notification:
						{
							title: `${username.val()}`,
							body: `${res.val()}`,
							icon: "default"
						}
					};
					return admin.messaging().sendToDevice(token_id, payload)
				});
			});
		});
	});
});
