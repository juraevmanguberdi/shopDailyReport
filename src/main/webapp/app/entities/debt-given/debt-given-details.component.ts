import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDebtGiven } from '@/shared/model/debt-given.model';
import DebtGivenService from './debt-given.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DebtGivenDetails extends Vue {
  @Inject('debtGivenService') private debtGivenService: () => DebtGivenService;
  @Inject('alertService') private alertService: () => AlertService;

  public debtGiven: IDebtGiven = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.debtGivenId) {
        vm.retrieveDebtGiven(to.params.debtGivenId);
      }
    });
  }

  public retrieveDebtGiven(debtGivenId) {
    this.debtGivenService()
      .find(debtGivenId)
      .then(res => {
        this.debtGiven = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
