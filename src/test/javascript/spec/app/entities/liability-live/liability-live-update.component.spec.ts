/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import LiabilityLiveUpdateComponent from '@/entities/liability-live/liability-live-update.vue';
import LiabilityLiveClass from '@/entities/liability-live/liability-live-update.component';
import LiabilityLiveService from '@/entities/liability-live/liability-live.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('LiabilityLive Management Update Component', () => {
    let wrapper: Wrapper<LiabilityLiveClass>;
    let comp: LiabilityLiveClass;
    let liabilityLiveServiceStub: SinonStubbedInstance<LiabilityLiveService>;

    beforeEach(() => {
      liabilityLiveServiceStub = sinon.createStubInstance<LiabilityLiveService>(LiabilityLiveService);

      wrapper = shallowMount<LiabilityLiveClass>(LiabilityLiveUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          liabilityLiveService: () => liabilityLiveServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.liabilityLive = entity;
        liabilityLiveServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(liabilityLiveServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.liabilityLive = entity;
        liabilityLiveServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(liabilityLiveServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLiabilityLive = { id: 123 };
        liabilityLiveServiceStub.find.resolves(foundLiabilityLive);
        liabilityLiveServiceStub.retrieve.resolves([foundLiabilityLive]);

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
