class MyCardAddCardViewModelFactory @Inject constructor(
    private val addCardUseCase: AddCardUseCase,
    private val getDeviceIdUseCase: GetDeviceIdUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MyCardAddCardViewModel::class.java)) {
            MyCardAddCardViewModel(addCardUseCase, getDeviceIdUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}