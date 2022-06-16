import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAssetRegistry } from '@/shared/model/asset-registry.model';
import AssetRegistryService from './asset-registry.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AssetRegistryDetails extends Vue {
  @Inject('assetRegistryService') private assetRegistryService: () => AssetRegistryService;
  @Inject('alertService') private alertService: () => AlertService;

  public assetRegistry: IAssetRegistry = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.assetRegistryId) {
        vm.retrieveAssetRegistry(to.params.assetRegistryId);
      }
    });
  }

  public retrieveAssetRegistry(assetRegistryId) {
    this.assetRegistryService()
      .find(assetRegistryId)
      .then(res => {
        this.assetRegistry = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
