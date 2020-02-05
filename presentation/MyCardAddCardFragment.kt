class MyCardAddCardFragment : BaseFragment<MyCardAddCardViewModel>() {

    companion object {

        private const val VALIDATE_CARD_EXTRA =
            "UI.SCREENS.MY_CARD.ADD.MYCARDADDCARDFRAGMENT.VALIDATE_CARD_EXTRA"

        fun getAddCardArgs(validateCardResponse: ValidateCardResponseEntity) = Bundle().apply {
            putSerializable(VALIDATE_CARD_EXTRA, validateCardResponse)
        }
    }

    @Inject
    lateinit var vmFactory: MyCardAddCardViewModelFactory

    lateinit var validateCardResponse: ValidateCardResponseEntity

    override fun layoutId() = R.layout.fragment_my_card_show_or_add_card

    override fun getViewModelClass() = MyCardAddCardViewModel::class.java

    override fun getViewModelFactory() = vmFactory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.addCardLiveData.observe(viewLifecycleOwner, Observer {
            Timber.w(it.toString())
            if (ResourceState.LOADING == it.status) {
                showProgress(getString(R.string.attaching_the_card))
            } else if (ResourceState.SUCCESS == it.status && it.data != null) {
                hideProgress()
                openMyCardScreen(it)
            } else if (ResourceState.ERROR == it.status) {
                showToast(it.error?.message, Toast.LENGTH_SHORT)
                hideProgress()
            }
        })
    }

    private fun openMyCardScreen(node: Resource<CardEntity>) {
        val bundleCard = MyCardShowMyCard.getCardShowArgs(node.data as CardEntity)
        val bundle = if (node.data.status == StatusEntity.ACTIVE) {
            ArgumentsRouter.getCardScreenTypeArgs(CardScreenType.REGULAR)
        } else Bundle()
        bundle.putAll(bundleCard)
        bundle.putBoolean(Constants.EXTRA_IS_USER_PRESENT, true)
        findNavController().navigate(R.id.myCardLoadMyCard, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
        setUpViews()
        addCard.setOnClickListener {
            validateCardResponse.run { viewModel.addCard(cardHolderName, cardNumber, expirationDate) }
        }
    }

    private fun initData() {
        validateCardResponse = arguments?.getSerializable(VALIDATE_CARD_EXTRA) as ValidateCardResponseEntity
    }

    private fun setUpViews() {
        addCard.visibility = View.VISIBLE
        textCardNumber.text = validateCardResponse.cardNumber
        textCardHolder.text = validateCardResponse.cardHolderName
        val expirationDate = validateCardResponse.expirationDate.toString(DomainConstants.DATE_FORMAT)
        textCardExpireDate.text = expirationDate
        addCard.text = getString(R.string.add_card_title)
        statusCard.visibility = View.GONE
    }
}