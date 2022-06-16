import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ILiabilityLive, LiabilityLive } from '@/shared/model/liability-live.model';
import LiabilityLiveService from './liability-live.service';

const validations: any = {
  liabilityLive: {
    totalLiabilities: {},
    supplier: {},
    bankLoan: {},
    other: {},
    code: {},
    notes: {},
  },
};

@Component({
  validations,
})
export default class LiabilityLiveUpdate extends Vue {
  @Inject('liabilityLiveService') private liabilityLiveService: () => LiabilityLiveService;
  @Inject('alertService') private alertService: () => AlertService;

  public liabilityLive: ILiabilityLive = new LiabilityLive();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.liabilityLiveId) {
        vm.retrieveLiabilityLive(to.params.liabilityLiveId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.liabilityLive.id) {
      this.liabilityLiveService()
        .update(this.liabilityLive)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.liabilityLive.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.liabilityLiveService()
        .create(this.liabilityLive)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.liabilityLive.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveLiabilityLive(liabilityLiveId): void {
    this.liabilityLiveService()
      .find(liabilityLiveId)
      .then(res => {
        this.liabilityLive = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
