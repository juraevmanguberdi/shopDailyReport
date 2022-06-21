import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const DebtGiven = () => import('@/entities/debt-given/debt-given.vue');
// prettier-ignore
const DebtGivenUpdate = () => import('@/entities/debt-given/debt-given-update.vue');
// prettier-ignore
const DebtGivenDetails = () => import('@/entities/debt-given/debt-given-details.vue');
// prettier-ignore
const DebtReturn = () => import('@/entities/debt-return/debt-return.vue');
// prettier-ignore
const DebtReturnUpdate = () => import('@/entities/debt-return/debt-return-update.vue');
// prettier-ignore
const DebtReturnDetails = () => import('@/entities/debt-return/debt-return-details.vue');
// prettier-ignore
const Expense = () => import('@/entities/expense/expense.vue');
// prettier-ignore
const ExpenseUpdate = () => import('@/entities/expense/expense-update.vue');
// prettier-ignore
const ExpenseDetails = () => import('@/entities/expense/expense-details.vue');
// prettier-ignore
const ExpenseType = () => import('@/entities/expense-type/expense-type.vue');
// prettier-ignore
const ExpenseTypeUpdate = () => import('@/entities/expense-type/expense-type-update.vue');
// prettier-ignore
const ExpenseTypeDetails = () => import('@/entities/expense-type/expense-type-details.vue');
// prettier-ignore
const DailyRegistryShop = () => import('@/entities/daily-registry-shop/daily-registry-shop.vue');
// prettier-ignore
const DailyRegistryShopUpdate = () => import('@/entities/daily-registry-shop/daily-registry-shop-update.vue');
// prettier-ignore
const DailyRegistryShopDetails = () => import('@/entities/daily-registry-shop/daily-registry-shop-details.vue');
// prettier-ignore
const OwnerExpense = () => import('@/entities/owner-expense/owner-expense.vue');
// prettier-ignore
const OwnerExpenseUpdate = () => import('@/entities/owner-expense/owner-expense-update.vue');
// prettier-ignore
const OwnerExpenseDetails = () => import('@/entities/owner-expense/owner-expense-details.vue');
// prettier-ignore
const OwnerExpenseType = () => import('@/entities/owner-expense-type/owner-expense-type.vue');
// prettier-ignore
const OwnerExpenseTypeUpdate = () => import('@/entities/owner-expense-type/owner-expense-type-update.vue');
// prettier-ignore
const OwnerExpenseTypeDetails = () => import('@/entities/owner-expense-type/owner-expense-type-details.vue');
// prettier-ignore
const Client = () => import('@/entities/client/client.vue');
// prettier-ignore
const ClientUpdate = () => import('@/entities/client/client-update.vue');
// prettier-ignore
const ClientDetails = () => import('@/entities/client/client-details.vue');
// prettier-ignore
const PaymentMethod = () => import('@/entities/payment-method/payment-method.vue');
// prettier-ignore
const PaymentMethodUpdate = () => import('@/entities/payment-method/payment-method-update.vue');
// prettier-ignore
const PaymentMethodDetails = () => import('@/entities/payment-method/payment-method-details.vue');
// prettier-ignore
const AssetRegistry = () => import('@/entities/asset-registry/asset-registry.vue');
// prettier-ignore
const AssetRegistryUpdate = () => import('@/entities/asset-registry/asset-registry-update.vue');
// prettier-ignore
const AssetRegistryDetails = () => import('@/entities/asset-registry/asset-registry-details.vue');
// prettier-ignore
const LiabilityRegistry = () => import('@/entities/liability-registry/liability-registry.vue');
// prettier-ignore
const LiabilityRegistryUpdate = () => import('@/entities/liability-registry/liability-registry-update.vue');
// prettier-ignore
const LiabilityRegistryDetails = () => import('@/entities/liability-registry/liability-registry-details.vue');
// prettier-ignore
const RequiredProduct = () => import('@/entities/required-product/required-product.vue');
// prettier-ignore
const RequiredProductUpdate = () => import('@/entities/required-product/required-product-update.vue');
// prettier-ignore
const RequiredProductDetails = () => import('@/entities/required-product/required-product-details.vue');
// prettier-ignore
const RequiredProductType = () => import('@/entities/required-product-type/required-product-type.vue');
// prettier-ignore
const RequiredProductTypeUpdate = () => import('@/entities/required-product-type/required-product-type-update.vue');
// prettier-ignore
const RequiredProductTypeDetails = () => import('@/entities/required-product-type/required-product-type-details.vue');
// prettier-ignore
const BorrowedDebt = () => import('@/entities/borrowed-debt/borrowed-debt.vue');
// prettier-ignore
const BorrowedDebtUpdate = () => import('@/entities/borrowed-debt/borrowed-debt-update.vue');
// prettier-ignore
const BorrowedDebtDetails = () => import('@/entities/borrowed-debt/borrowed-debt-details.vue');
// prettier-ignore
const BorrowedDebtType = () => import('@/entities/borrowed-debt-type/borrowed-debt-type.vue');
// prettier-ignore
const BorrowedDebtTypeUpdate = () => import('@/entities/borrowed-debt-type/borrowed-debt-type-update.vue');
// prettier-ignore
const BorrowedDebtTypeDetails = () => import('@/entities/borrowed-debt-type/borrowed-debt-type-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'debt-given',
      name: 'DebtGiven',
      component: DebtGiven,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'debt-given/new',
      name: 'DebtGivenCreate',
      component: DebtGivenUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'debt-given/:debtGivenId/edit',
      name: 'DebtGivenEdit',
      component: DebtGivenUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'debt-given/:debtGivenId/view',
      name: 'DebtGivenView',
      component: DebtGivenDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'debt-return',
      name: 'DebtReturn',
      component: DebtReturn,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'debt-return/new',
      name: 'DebtReturnCreate',
      component: DebtReturnUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'debt-return/:debtReturnId/edit',
      name: 'DebtReturnEdit',
      component: DebtReturnUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'debt-return/:debtReturnId/view',
      name: 'DebtReturnView',
      component: DebtReturnDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense',
      name: 'Expense',
      component: Expense,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense/new',
      name: 'ExpenseCreate',
      component: ExpenseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense/:expenseId/edit',
      name: 'ExpenseEdit',
      component: ExpenseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense/:expenseId/view',
      name: 'ExpenseView',
      component: ExpenseDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense-type',
      name: 'ExpenseType',
      component: ExpenseType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense-type/new',
      name: 'ExpenseTypeCreate',
      component: ExpenseTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense-type/:expenseTypeId/edit',
      name: 'ExpenseTypeEdit',
      component: ExpenseTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense-type/:expenseTypeId/view',
      name: 'ExpenseTypeView',
      component: ExpenseTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'daily-registry-shop',
      name: 'DailyRegistryShop',
      component: DailyRegistryShop,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'daily-registry-shop/new',
      name: 'DailyRegistryShopCreate',
      component: DailyRegistryShopUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'daily-registry-shop/:dailyRegistryShopId/edit',
      name: 'DailyRegistryShopEdit',
      component: DailyRegistryShopUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'daily-registry-shop/:dailyRegistryShopId/view',
      name: 'DailyRegistryShopView',
      component: DailyRegistryShopDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'owner-expense',
      name: 'OwnerExpense',
      component: OwnerExpense,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'owner-expense/new',
      name: 'OwnerExpenseCreate',
      component: OwnerExpenseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'owner-expense/:ownerExpenseId/edit',
      name: 'OwnerExpenseEdit',
      component: OwnerExpenseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'owner-expense/:ownerExpenseId/view',
      name: 'OwnerExpenseView',
      component: OwnerExpenseDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'owner-expense-type',
      name: 'OwnerExpenseType',
      component: OwnerExpenseType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'owner-expense-type/new',
      name: 'OwnerExpenseTypeCreate',
      component: OwnerExpenseTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'owner-expense-type/:ownerExpenseTypeId/edit',
      name: 'OwnerExpenseTypeEdit',
      component: OwnerExpenseTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'owner-expense-type/:ownerExpenseTypeId/view',
      name: 'OwnerExpenseTypeView',
      component: OwnerExpenseTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client',
      name: 'Client',
      component: Client,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/new',
      name: 'ClientCreate',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/edit',
      name: 'ClientEdit',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/view',
      name: 'ClientView',
      component: ClientDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-method',
      name: 'PaymentMethod',
      component: PaymentMethod,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-method/new',
      name: 'PaymentMethodCreate',
      component: PaymentMethodUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-method/:paymentMethodId/edit',
      name: 'PaymentMethodEdit',
      component: PaymentMethodUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-method/:paymentMethodId/view',
      name: 'PaymentMethodView',
      component: PaymentMethodDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-registry',
      name: 'AssetRegistry',
      component: AssetRegistry,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-registry/new',
      name: 'AssetRegistryCreate',
      component: AssetRegistryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-registry/:assetRegistryId/edit',
      name: 'AssetRegistryEdit',
      component: AssetRegistryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-registry/:assetRegistryId/view',
      name: 'AssetRegistryView',
      component: AssetRegistryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'liability-registry',
      name: 'LiabilityRegistry',
      component: LiabilityRegistry,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'liability-registry/new',
      name: 'LiabilityRegistryCreate',
      component: LiabilityRegistryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'liability-registry/:liabilityRegistryId/edit',
      name: 'LiabilityRegistryEdit',
      component: LiabilityRegistryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'liability-registry/:liabilityRegistryId/view',
      name: 'LiabilityRegistryView',
      component: LiabilityRegistryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'required-product',
      name: 'RequiredProduct',
      component: RequiredProduct,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'required-product/new',
      name: 'RequiredProductCreate',
      component: RequiredProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'required-product/:requiredProductId/edit',
      name: 'RequiredProductEdit',
      component: RequiredProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'required-product/:requiredProductId/view',
      name: 'RequiredProductView',
      component: RequiredProductDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'required-product-type',
      name: 'RequiredProductType',
      component: RequiredProductType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'required-product-type/new',
      name: 'RequiredProductTypeCreate',
      component: RequiredProductTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'required-product-type/:requiredProductTypeId/edit',
      name: 'RequiredProductTypeEdit',
      component: RequiredProductTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'required-product-type/:requiredProductTypeId/view',
      name: 'RequiredProductTypeView',
      component: RequiredProductTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'borrowed-debt',
      name: 'BorrowedDebt',
      component: BorrowedDebt,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'borrowed-debt/new',
      name: 'BorrowedDebtCreate',
      component: BorrowedDebtUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'borrowed-debt/:borrowedDebtId/edit',
      name: 'BorrowedDebtEdit',
      component: BorrowedDebtUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'borrowed-debt/:borrowedDebtId/view',
      name: 'BorrowedDebtView',
      component: BorrowedDebtDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'borrowed-debt-type',
      name: 'BorrowedDebtType',
      component: BorrowedDebtType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'borrowed-debt-type/new',
      name: 'BorrowedDebtTypeCreate',
      component: BorrowedDebtTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'borrowed-debt-type/:borrowedDebtTypeId/edit',
      name: 'BorrowedDebtTypeEdit',
      component: BorrowedDebtTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'borrowed-debt-type/:borrowedDebtTypeId/view',
      name: 'BorrowedDebtTypeView',
      component: BorrowedDebtTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
