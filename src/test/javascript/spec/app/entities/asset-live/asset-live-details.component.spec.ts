/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AssetLiveDetailComponent from '@/entities/asset-live/asset-live-details.vue';
import AssetLiveClass from '@/entities/asset-live/asset-live-details.component';
import AssetLiveService from '@/entities/asset-live/asset-live.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AssetLive Management Detail Component', () => {
    let wrapper: Wrapper<AssetLiveClass>;
    let comp: AssetLiveClass;
    let assetLiveServiceStub: SinonStubbedInstance<AssetLiveService>;

    beforeEach(() => {
      assetLiveServiceStub = sinon.createStubInstance<AssetLiveService>(AssetLiveService);

      wrapper = shallowMount<AssetLiveClass>(AssetLiveDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { assetLiveService: () => assetLiveServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAssetLive = { id: 123 };
        assetLiveServiceStub.find.resolves(foundAssetLive);

        // WHEN
        comp.retrieveAssetLive(123);
        await comp.$nextTick();

        // THEN
        expect(comp.assetLive).toBe(foundAssetLive);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAssetLive = { id: 123 };
        assetLiveServiceStub.find.resolves(foundAssetLive);

        // WHEN
        comp.beforeRouteEnter({ params: { assetLiveId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.assetLive).toBe(foundAssetLive);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
