package com.example.experiments2.pages.menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.experiments2.component.dialog.GameMessage
import com.example.experiments2.constant.Constant.MAX_PLAYER
import com.example.experiments2.constant.Constant.MIN_PLAYER
import com.example.experiments2.databinding.FragmentCreateGameBinding
import com.example.experiments2.network.remote.response.game.room.RoomData
import com.example.experiments2.network.remote.response.game.room.RoomEnum
import com.example.experiments2.util.Util
import com.example.experiments2.util.Util.toDisable
import com.example.experiments2.util.Util.toEnable
import com.example.experiments2.viewmodel.ViewModelEnum

private const val CREATE_ROOM = "create_room"

class CreateRoomFragment : Fragment() {

    private lateinit var binding: FragmentCreateGameBinding
    private lateinit var createJoinViewModel: CreateJoinViewModel
    private lateinit var errorMessage: GameMessage

    private var createRoomCard: ((RoomData, () -> Unit) -> Unit)? = null
    private var onShowLoading: (() -> Unit)? = null
    private var onHideLoading: (() -> Unit)? = null
    private var onForceLogout: (() -> Unit)? = null
    private var updateRoomCard: ((RoomData) -> Unit)? = null

    private var roomId: String? = null

    fun setCreateRoomCard(
        createRoomCard: ((RoomData, () -> Unit) -> Unit),
        onShowLoading: (() -> Unit),
        onHideLoading: (() -> Unit),
        onForceLogout: (() -> Unit),
        updateRoomCard: ((RoomData) -> Unit)
    ) {
        this.createRoomCard = createRoomCard
        this.onShowLoading = onShowLoading
        this.onHideLoading = onHideLoading
        this.onForceLogout = onForceLogout
        this.updateRoomCard = updateRoomCard
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (savedInstanceState != null) {
            val data = savedInstanceState.getParcelable(CREATE_ROOM) as? FragmentBehaviorData
            if (data != null) {
                setCreateRoomCard(
                    data.createRoomCard,
                    data.onShowLoading,
                    data.onHideLoading,
                    data.onForceLogout,
                    data.updateRoomCard
                )
            }
        }

        binding = FragmentCreateGameBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (activity != null) {
            initViewModel()
            initComponent()
        }
    }

    private fun initViewModel() {
        createJoinViewModel = ViewModelProvider(this)[CreateJoinViewModel::class.java]

        createJoinViewModel.vmData.observe(viewLifecycleOwner) { vmData ->
            if (vmData != null) {
                when (vmData.status) {
                    ViewModelEnum.LOADING -> {
                        onShowLoading?.invoke()
                        onShowFragmentLoading()
                    }
                    ViewModelEnum.SUCCESS -> {
                        val data = vmData.data

                        if (data != null) {
                            val roomData = data.roomData

                            when(data.state) {
                                CreateJoinEnum.CREATE -> {
                                    roomId = roomData.roomId

                                    if (roomData.roomMode == RoomEnum.ONLINE) {
                                        createRoomCard?.let {
                                            it(roomData) {
                                                createJoinViewModel.leaveTheRoom(
                                                    requireContext(),
                                                    roomData.roomId
                                                )
                                            }
                                        }
                                    } else {
                                        createRoomCard?.let {
                                            it(roomData) { roomId = null }
                                        }
                                    }
                                }
                                CreateJoinEnum.LISTEN -> {
                                    updateRoomCard?.invoke(roomData)
                                }
                                CreateJoinEnum.LEAVE -> {
                                    roomId = null
                                }
                                CreateJoinEnum.FORCE_LOGOUT -> {
                                    roomId = null
                                    onForceLogout?.invoke()
                                }
                                else -> {}
                            }
                        }
                    }
                    ViewModelEnum.COMPLETE -> {
                        onHideLoading?.invoke()
                        onHideFragmentLoading()
                    }
                    else -> {}
                }

                Util.handleErrorMessage(errorMessage, vmData.status, vmData.errorMessage)
            }
        }
    }

    private fun initComponent() {
        errorMessage = GameMessage.newInstance(requireContext())

        binding.ivCreateGamePlusPlayer.setOnClickListener {
            handleTotalPlayer(binding.etCreateGameSetupTotalPlayer.text.toString().toInt() + 1)
        }

        binding.ivCreateGameMinusPlayer.setOnClickListener {
            handleTotalPlayer(binding.etCreateGameSetupTotalPlayer.text.toString().toInt() - 1)
        }

        binding.btnStart.setOnClickListener {
            with(binding) {
                val roomName = if (etCreateGameTitle.text.isNullOrEmpty()) etCreateGameTitle.hint
                else etCreateGameTitle.text

                val roomEnum = if (radioButtonOnline.isChecked) RoomEnum.ONLINE
                else RoomEnum.BOTS

                val maxPlayer = etCreateGameSetupTotalPlayer.text.toString().toInt()

                createJoinViewModel.hostTheRoom(
                    requireContext(), roomName.toString(), roomEnum, maxPlayer
                )
            }
        }
    }

    private fun handleTotalPlayer(total: Int) {
        if (total >= MAX_PLAYER) {
            binding.etCreateGameSetupTotalPlayer.setText(MAX_PLAYER.toString())
        } else if (total <= MIN_PLAYER) {
            binding.etCreateGameSetupTotalPlayer.setText(MIN_PLAYER.toString())
        } else {
            binding.etCreateGameSetupTotalPlayer.setText(total.toString())
        }
    }

    private fun onShowFragmentLoading() {
        binding.btnStart.toDisable()
        binding.etCreateGameTitle.isEnabled = false
        binding.ivCreateGamePlusPlayer.toDisable()
        binding.ivCreateGameMinusPlayer.toDisable()
        binding.radioButtonOnline.isEnabled = false
        binding.radioButtonAIBots.isEnabled = false
    }

    private fun onHideFragmentLoading() {
        binding.btnStart.toEnable()
        binding.etCreateGameTitle.isEnabled = true
        binding.ivCreateGamePlusPlayer.toEnable()
        binding.ivCreateGameMinusPlayer.toEnable()
        binding.radioButtonOnline.isEnabled = true
        binding.radioButtonAIBots.isEnabled = true
    }

    override fun onStop() {
        CreateJoinService.startService(requireContext(), roomId)
        super.onStop()
    }

    override fun onResume() {
        CreateJoinService.stopService(requireContext())
        if (roomId != null) {
            createJoinViewModel.refreshTheRoom(requireContext(), roomId!!)
        }

        super.onResume()
    }
}