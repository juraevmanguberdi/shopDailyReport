import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDebtReturn } from '@/shared/model/debt-return.model';
import DebtReturnService from './debt-return.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DebtReturnDetails extends Vue {
  @Inject('debtReturnService') private debtReturnService: () => DebtReturnService;
  @Inject('alertService') private alertService: () => AlertService;

  public debtReturn: IDebtReturn = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.debtReturnId) {
        vm.retrieveDebtReturn(to.params.debtReturnId);
      }
    });
  }

  public retrieveDebtReturn(debtReturnId) {
    this.debtReturnService()
      .find(debtReturnId)
      .then(res => {
        this.debtReturn = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
