/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LiabilityRegistryDetailComponent from '@/entities/liability-registry/liability-registry-details.vue';
import LiabilityRegistryClass from '@/entities/liability-registry/liability-registry-details.component';
import LiabilityRegistryService from '@/entities/liability-registry/liability-registry.service';
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
  describe('LiabilityRegistry Management Detail Component', () => {
    let wrapper: Wrapper<LiabilityRegistryClass>;
    let comp: LiabilityRegistryClass;
    let liabilityRegistryServiceStub: SinonStubbedInstance<LiabilityRegistryService>;

    beforeEach(() => {
      liabilityRegistryServiceStub = sinon.createStubInstance<LiabilityRegistryService>(LiabilityRegistryService);

      wrapper = shallowMount<LiabilityRegistryClass>(LiabilityRegistryDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { liabilityRegistryService: () => liabilityRegistryServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLiabilityRegistry = { id: 123 };
        liabilityRegistryServiceStub.find.resolves(foundLiabilityRegistry);

        // WHEN
        comp.retrieveLiabilityRegistry(123);
        await comp.$nextTick();

        // THEN
        expect(comp.liabilityRegistry).toBe(foundLiabilityRegistry);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLiabilityRegistry = { id: 123 };
        liabilityRegistryServiceStub.find.resolves(foundLiabilityRegistry);

        // WHEN
        comp.beforeRouteEnter({ params: { liabilityRegistryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.liabilityRegistry).toBe(foundLiabilityRegistry);
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
