class MyCardAddCardViewModel(
    private val addCardUseCase: AddCardUseCase,
    private val getDeviceIdUseCase: GetDeviceIdUseCase) : BaseViewModel() {

    val addCardLiveData = MutableLiveData<Resource<CardEntity>>()

    fun addCard(cardholderName: String, cardNumber: String, expirationDate: LocalDate) {
        compositeDisposable.add(
                getDeviceIdUseCase.execute()
                    .flatMap {
                        val addCardEntity = AddCardEntity(cardholderName, cardNumber, expirationDate, Device(it))
                        addCardUseCase.execute(addCardEntity)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(BaseDisposableSingleSubscriber(addCardLiveData))
        )
    }
}