package ru.yandex.yamblz.playlist2.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ru.yandex.yamblz.playlist2.di.modules.AppModule;
import ru.yandex.yamblz.playlist2.ui.activities.MainActivity;
import ru.yandex.yamblz.playlist2.ui.fragments.DescFragment;
import ru.yandex.yamblz.playlist2.ui.fragments.ListFragment;
import ru.yandex.yamblz.playlist2.ui.fragments.PreviewFragment;
import ru.yandex.yamblz.playlist2.ui.fragments.TabsFragment;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(DescFragment descFragment);

    void inject(PreviewFragment previewFragment);

    void inject(ListFragment listFragment);

    void inject(TabsFragment tabsFragment);
}
