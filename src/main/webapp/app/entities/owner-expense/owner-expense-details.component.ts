import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOwnerExpense } from '@/shared/model/owner-expense.model';
import OwnerExpenseService from './owner-expense.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class OwnerExpenseDetails extends Vue {
  @Inject('ownerExpenseService') private ownerExpenseService: () => OwnerExpenseService;
  @Inject('alertService') private alertService: () => AlertService;

  public ownerExpense: IOwnerExpense = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ownerExpenseId) {
        vm.retrieveOwnerExpense(to.params.ownerExpenseId);
      }
    });
  }

  public retrieveOwnerExpense(ownerExpenseId) {
    this.ownerExpenseService()
      .find(ownerExpenseId)
      .then(res => {
        this.ownerExpense = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
