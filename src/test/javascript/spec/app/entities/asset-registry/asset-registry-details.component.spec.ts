/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AssetRegistryDetailComponent from '@/entities/asset-registry/asset-registry-details.vue';
import AssetRegistryClass from '@/entities/asset-registry/asset-registry-details.component';
import AssetRegistryService from '@/entities/asset-registry/asset-registry.service';
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
  describe('AssetRegistry Management Detail Component', () => {
    let wrapper: Wrapper<AssetRegistryClass>;
    let comp: AssetRegistryClass;
    let assetRegistryServiceStub: SinonStubbedInstance<AssetRegistryService>;

    beforeEach(() => {
      assetRegistryServiceStub = sinon.createStubInstance<AssetRegistryService>(AssetRegistryService);

      wrapper = shallowMount<AssetRegistryClass>(AssetRegistryDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { assetRegistryService: () => assetRegistryServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAssetRegistry = { id: 123 };
        assetRegistryServiceStub.find.resolves(foundAssetRegistry);

        // WHEN
        comp.retrieveAssetRegistry(123);
        await comp.$nextTick();

        // THEN
        expect(comp.assetRegistry).toBe(foundAssetRegistry);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAssetRegistry = { id: 123 };
        assetRegistryServiceStub.find.resolves(foundAssetRegistry);

        // WHEN
        comp.beforeRouteEnter({ params: { assetRegistryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.assetRegistry).toBe(foundAssetRegistry);
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
