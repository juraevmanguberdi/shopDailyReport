import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAssetLive } from '@/shared/model/asset-live.model';
import AssetLiveService from './asset-live.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AssetLiveDetails extends Vue {
  @Inject('assetLiveService') private assetLiveService: () => AssetLiveService;
  @Inject('alertService') private alertService: () => AlertService;

  public assetLive: IAssetLive = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.assetLiveId) {
        vm.retrieveAssetLive(to.params.assetLiveId);
      }
    });
  }

  public retrieveAssetLive(assetLiveId) {
    this.assetLiveService()
      .find(assetLiveId)
      .then(res => {
        this.assetLive = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
