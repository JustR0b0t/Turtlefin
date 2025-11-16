@file:JvmName("ModelUtils")

package app.turtlefin.androidtv.util.sdk

import app.turtlefin.androidtv.auth.model.PublicUser
import app.turtlefin.androidtv.auth.model.Server
import app.turtlefin.androidtv.util.apiclient.primaryImage
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo
import org.jellyfin.sdk.model.api.UserDto
import org.jellyfin.sdk.model.serializer.toUUID
import org.jellyfin.sdk.model.serializer.toUUIDOrNull

fun ServerDiscoveryInfo.toServer(): Server = Server(
	id = id.toUUID(),
	name = name,
	address = address,
)

fun UserDto.toPublicUser(): PublicUser? {
	return PublicUser(
		id = id,
		name = name ?: return null,
		serverId = serverId?.toUUIDOrNull() ?: return null,
		accessToken = null,
		imageTag = primaryImage?.tag
	)
}
