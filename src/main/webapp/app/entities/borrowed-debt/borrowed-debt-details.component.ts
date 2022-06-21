import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBorrowedDebt } from '@/shared/model/borrowed-debt.model';
import BorrowedDebtService from './borrowed-debt.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class BorrowedDebtDetails extends Vue {
  @Inject('borrowedDebtService') private borrowedDebtService: () => BorrowedDebtService;
  @Inject('alertService') private alertService: () => AlertService;

  public borrowedDebt: IBorrowedDebt = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.borrowedDebtId) {
        vm.retrieveBorrowedDebt(to.params.borrowedDebtId);
      }
    });
  }

  public retrieveBorrowedDebt(borrowedDebtId) {
    this.borrowedDebtService()
      .find(borrowedDebtId)
      .then(res => {
        this.borrowedDebt = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
