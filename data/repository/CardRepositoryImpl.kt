class CardRepositoryImpl @Inject constructor(
    private val store: CardStore,
    private val cacheStore: CacheStore
) : CardRepository {

    override fun addCard(request: AddCardEntity) = store.addCard(request)

    override fun getCurrentCard() = store.getCurrentCard()

    override fun getMyCard(token: String) = store.getMyCard(token)

    override fun validateCard(request: ValidateCardRequestEntity) = store.validateCard(request)

    override fun getCards() = store.getCards()

    override fun setHideFlagExpirationDialog(id: Long) = cacheStore.setHideFlagExpirationDialog(id)

    override fun getFlagExpirationDialog(id: Long?) = cacheStore.getFlagExpirationDialog(id)

    override fun checkCard(request: CheckCardRequestEntity) = store.checkCard(request)
}