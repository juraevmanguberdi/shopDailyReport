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
import AssetLiveService from './asset-live/asset-live.service';
import AssetRegistryService from './asset-registry/asset-registry.service';
import LiabilityRegistryService from './liability-registry/liability-registry.service';
import LiabilityLiveService from './liability-live/liability-live.service';
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
  @Provide('assetLiveService') private assetLiveService = () => new AssetLiveService();
  @Provide('assetRegistryService') private assetRegistryService = () => new AssetRegistryService();
  @Provide('liabilityRegistryService') private liabilityRegistryService = () => new LiabilityRegistryService();
  @Provide('liabilityLiveService') private liabilityLiveService = () => new LiabilityLiveService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
