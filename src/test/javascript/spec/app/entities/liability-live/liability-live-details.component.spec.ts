/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LiabilityLiveDetailComponent from '@/entities/liability-live/liability-live-details.vue';
import LiabilityLiveClass from '@/entities/liability-live/liability-live-details.component';
import LiabilityLiveService from '@/entities/liability-live/liability-live.service';
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
  describe('LiabilityLive Management Detail Component', () => {
    let wrapper: Wrapper<LiabilityLiveClass>;
    let comp: LiabilityLiveClass;
    let liabilityLiveServiceStub: SinonStubbedInstance<LiabilityLiveService>;

    beforeEach(() => {
      liabilityLiveServiceStub = sinon.createStubInstance<LiabilityLiveService>(LiabilityLiveService);

      wrapper = shallowMount<LiabilityLiveClass>(LiabilityLiveDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { liabilityLiveService: () => liabilityLiveServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLiabilityLive = { id: 123 };
        liabilityLiveServiceStub.find.resolves(foundLiabilityLive);

        // WHEN
        comp.retrieveLiabilityLive(123);
        await comp.$nextTick();

        // THEN
        expect(comp.liabilityLive).toBe(foundLiabilityLive);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLiabilityLive = { id: 123 };
        liabilityLiveServiceStub.find.resolves(foundLiabilityLive);

        // WHEN
        comp.beforeRouteEnter({ params: { liabilityLiveId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.liabilityLive).toBe(foundLiabilityLive);
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
