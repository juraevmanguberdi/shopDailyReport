import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILiabilityLive } from '@/shared/model/liability-live.model';
import LiabilityLiveService from './liability-live.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class LiabilityLiveDetails extends Vue {
  @Inject('liabilityLiveService') private liabilityLiveService: () => LiabilityLiveService;
  @Inject('alertService') private alertService: () => AlertService;

  public liabilityLive: ILiabilityLive = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.liabilityLiveId) {
        vm.retrieveLiabilityLive(to.params.liabilityLiveId);
      }
    });
  }

  public retrieveLiabilityLive(liabilityLiveId) {
    this.liabilityLiveService()
      .find(liabilityLiveId)
      .then(res => {
        this.liabilityLive = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
