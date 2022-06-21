import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import DebtGivenService from './debt-given/debt-given.service';
import DebtReturnService from './debt-return/debt-return.service';
import ExpenseService from './expense/expense.service';
import ExpenseTypeService from './expense-type/expense-type.service';
import DailyRegistryShopService from './daily-registry-shop/daily-registry-shop.service';
import OwnerExpenseService from './owner-expense/owner-expense.service';
import OwnerExpenseTypeService from './owner-expense-type/owner-expense-type.service';
import ClientService from './client/client.service';
import PaymentMethodService from './payment-method/payment-method.service';
import AssetRegistryService from './asset-registry/asset-registry.service';
import LiabilityRegistryService from './liability-registry/liability-registry.service';
import RequiredProductService from './required-product/required-product.service';
import RequiredProductTypeService from './required-product-type/required-product-type.service';
import BorrowedDebtService from './borrowed-debt/borrowed-debt.service';
import BorrowedDebtTypeService from './borrowed-debt-type/borrowed-debt-type.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('debtGivenService') private debtGivenService = () => new DebtGivenService();
  @Provide('debtReturnService') private debtReturnService = () => new DebtReturnService();
  @Provide('expenseService') private expenseService = () => new ExpenseService();
  @Provide('expenseTypeService') private expenseTypeService = () => new ExpenseTypeService();
  @Provide('dailyRegistryShopService') private dailyRegistryShopService = () => new DailyRegistryShopService();
  @Provide('ownerExpenseService') private ownerExpenseService = () => new OwnerExpenseService();
  @Provide('ownerExpenseTypeService') private ownerExpenseTypeService = () => new OwnerExpenseTypeService();
  @Provide('clientService') private clientService = () => new ClientService();
  @Provide('paymentMethodService') private paymentMethodService = () => new PaymentMethodService();
  @Provide('assetRegistryService') private assetRegistryService = () => new AssetRegistryService();
  @Provide('liabilityRegistryService') private liabilityRegistryService = () => new LiabilityRegistryService();
  @Provide('requiredProductService') private requiredProductService = () => new RequiredProductService();
  @Provide('requiredProductTypeService') private requiredProductTypeService = () => new RequiredProductTypeService();
  @Provide('borrowedDebtService') private borrowedDebtService = () => new BorrowedDebtService();
  @Provide('borrowedDebtTypeService') private borrowedDebtTypeService = () => new BorrowedDebtTypeService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
