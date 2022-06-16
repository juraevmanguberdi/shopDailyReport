import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILiabilityRegistry } from '@/shared/model/liability-registry.model';
import LiabilityRegistryService from './liability-registry.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class LiabilityRegistryDetails extends Vue {
  @Inject('liabilityRegistryService') private liabilityRegistryService: () => LiabilityRegistryService;
  @Inject('alertService') private alertService: () => AlertService;

  public liabilityRegistry: ILiabilityRegistry = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.liabilityRegistryId) {
        vm.retrieveLiabilityRegistry(to.params.liabilityRegistryId);
      }
    });
  }

  public retrieveLiabilityRegistry(liabilityRegistryId) {
    this.liabilityRegistryService()
      .find(liabilityRegistryId)
      .then(res => {
        this.liabilityRegistry = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
