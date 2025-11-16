package app.turtlefin.androidtv.ui.startup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import app.turtlefin.androidtv.R
import app.turtlefin.androidtv.auth.model.ApiClientErrorLoginState
import app.turtlefin.androidtv.auth.model.AuthenticatedState
import app.turtlefin.androidtv.auth.model.AuthenticatingState
import app.turtlefin.androidtv.auth.model.ConnectedQuickConnectState
import app.turtlefin.androidtv.auth.model.PendingQuickConnectState
import app.turtlefin.androidtv.auth.model.RequireSignInState
import app.turtlefin.androidtv.auth.model.ServerUnavailableState
import app.turtlefin.androidtv.auth.model.ServerVersionNotSupported
import app.turtlefin.androidtv.auth.model.UnavailableQuickConnectState
import app.turtlefin.androidtv.auth.model.UnknownQuickConnectState
import app.turtlefin.androidtv.auth.repository.ServerRepository
import app.turtlefin.androidtv.databinding.FragmentUserLoginQuickConnectBinding
import app.turtlefin.androidtv.ui.startup.UserLoginViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class UserLoginQuickConnectFragment : Fragment() {
	private val userLoginViewModel: UserLoginViewModel by activityViewModel()
	private var _binding: FragmentUserLoginQuickConnectBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentUserLoginQuickConnectBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		userLoginViewModel.clearLoginState()

		lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				userLoginViewModel.initiateQuickconnect()

				// React to Quick Connect specific state
				userLoginViewModel.quickConnectState.onEach { state ->
					when (state) {
						is PendingQuickConnectState -> {
							binding.quickConnectCode.text = state.code.formatCode()
							binding.loading.isVisible = false
						}

						UnavailableQuickConnectState,
						UnknownQuickConnectState,
						ConnectedQuickConnectState -> binding.loading.isVisible = true
					}
				}.launchIn(this)

				// React to login state
				userLoginViewModel.loginState.onEach { state ->
					when (state) {
						is ServerVersionNotSupported -> binding.error.setText(
							getString(
								R.string.server_issue_outdated_version,
								state.server.version,
								ServerRepository.recommendedServerVersion.toString()
							)
						)

						AuthenticatingState -> binding.error.setText(R.string.login_authenticating)
						RequireSignInState -> binding.error.setText(R.string.login_invalid_credentials)
						ServerUnavailableState,
						is ApiClientErrorLoginState -> binding.error.setText(R.string.login_server_unavailable)
						// Do nothing because the activity will respond to the new session
						AuthenticatedState -> Unit
						// Not initialized
						null -> Unit
					}
				}.launchIn(this)
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()

		_binding = null
	}

	/**
	 * Add space after every 3 characters so "420420" becomes "420 420".
	 */
	private fun String.formatCode() = buildString {
		@Suppress("MagicNumber")
		val interval = 3
		this@formatCode.forEachIndexed { index, character ->
			if (index != 0 && index % interval == 0) append(" ")
			append(character)
		}
	}
}
